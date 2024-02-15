kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic customer --delete --if-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-request --delete --if-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-response --delete --if-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic restaurant-approval-request --delete --if-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic restaurant-approval-response --delete --if-exists

kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic customer --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-request --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-response --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic restaurant-approval-request --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic restaurant-approval-response --create --partitions 3 --replication-factor 3 --if-not-exists