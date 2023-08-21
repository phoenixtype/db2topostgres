package dev.phoenixtype.db2topostgres.service;

import dev.phoenixtype.db2topostgres.model.StudentRecord;
import dev.phoenixtype.db2topostgres.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final String KAFKA_TOPIC = "db2.DB2INST1.STUDENT";
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final StudentsRepository studentsRepository;

    @Transactional
    @KafkaListener(topics = KAFKA_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void processMessage(GenericRecord record) {
        logger.info("Received Avro Record: " + record);

        try {
            if (record == null) {
                logger.error("Received an empty or null message.");
                return;
            }

            String op = record.get("op").toString();

            if ("d".equals(op)) {
                GenericRecord beforeNode = (GenericRecord) record.get("before");
                if (beforeNode != null) {
                    int id = Integer.parseInt(beforeNode.get("ID").toString());
                    logger.info("id value: " + id);
                    studentsRepository.deleteById(id);
                    logger.info("Deleted record with id: " + id);
                }
            } else if ("u".equals(op)) {
                GenericRecord beforeNode = (GenericRecord) record.get("before");
                GenericRecord afterNode = (GenericRecord) record.get("after");

                logger.info("Before node payload " + beforeNode);
                logger.info("After node payload " + afterNode);

                if (beforeNode != null && afterNode != null) {
                    int id = Integer.parseInt(beforeNode.get("ID").toString());
                    String newName = afterNode.get("NAME").toString();
                    String newEmail = afterNode.get("EMAIL").toString();

                    logger.info("The update id value is "+ id);
                    logger.info("The update name value is " + newName);
                    logger.info("The update email value is " + newEmail);

                    StudentRecord existingStudentRecord = studentsRepository.findById(id).orElse(null);

                    System.out.println("The existing student record object is " + existingStudentRecord);

                    if (existingStudentRecord != null) {
                        existingStudentRecord.setName(newName);
                        existingStudentRecord.setEmail(newEmail);
                        studentsRepository.save(existingStudentRecord);
                        logger.info("Updated record with id " + id + ": " + existingStudentRecord);
                    } else {
                        logger.info("Record with id " + id + " not found for update!");
                    }
                }
            } else if ("c".equals(op)) {
                GenericRecord afterNode = (GenericRecord) record.get("after");
                if (afterNode != null) {
                    int id = Integer.parseInt(afterNode.get("ID").toString());
                    String name = afterNode.get("NAME").toString();
                    String email = afterNode.get("EMAIL").toString();
                    StudentRecord newStudentRecord = new StudentRecord();
                    newStudentRecord.setId(id);
                    newStudentRecord.setName(name);
                    newStudentRecord.setEmail(email);
                    studentsRepository.save(newStudentRecord);
                    logger.info("Inserted new record: " + newStudentRecord);
                }
            } else {
                logger.info("Unknown operation type: " + op);
            }
        } catch (NumberFormatException e) {
            logger.error("Error: Unable to parse the 'id' field.", e);
        } catch (Exception e) {
            logger.error("Error: An unexpected error occurred while processing the record.", e);
        }
    }
}