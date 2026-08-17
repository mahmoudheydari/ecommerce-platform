rootProject.name = "ecommerce-platform"

include(
    "common:common-core",
    "identity-service",
    "product-service",
    "cart-service",
    )

project(":common:common-core").projectDir = file("common/common-core")
