docker run --tty \
--network db2topostgres_default \
confluentinc/cp-kafkacat \
kafkacat -b kafka:29092 -C \
-s key=s -s value=avro \
-r http://schema-registry:8081 \
-t db2.AKUMA.STUDENT