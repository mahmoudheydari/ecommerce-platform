rootProject.name = "ecommerce-platform"

include(
    "common:common-core",
    "common:common-data",
    "identity-service",
    "product-service",
    "cart-service",
)

project(":common:common-core").projectDir = file("common/common-core")
project(":common:common-data").projectDir = file("common/common-data")
