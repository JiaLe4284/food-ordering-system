gcloud auth login

docker tag com.food.ordering.system/order.service:1.0-SNAPSHOT asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/order.service:$1
docker tag com.food.ordering.system/payment.service:1.0-SNAPSHOT asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/payment.service:$1
docker tag com.food.ordering.system/restaurant.service:1.0-SNAPSHOT asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/restaurant.service:$1
docker tag com.food.ordering.system/customer.service:1.0-SNAPSHOT asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/customer.service:$1

docker push asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/order.service:$1
docker push asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/payment.service:$1
docker push asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/restaurant.service:$1
docker push asia-southeast1-docker.pkg.dev/peppy-caster-414418/food-ordering-system-repo/customer.service:$1