package com.thoughtworks.bridge2delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.swagger.SwaggerUtils;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SwaggerUtilsTest {
    private final static String VERSION = "1.0.0";
    private final static String TITLE = "Order Aggregation Server";

    @Test
    public void should_parse_swagger_info_to_object() throws JsonProcessingException {
        SwaggerInfo info = SwaggerUtils.parseSwaggerJson(jsonString());

        Assertions.assertEquals(info.getTitle(), TITLE);
        Assertions.assertEquals(info.getVersion(), VERSION);
    }

    @Test
    public void should_throw_exception_when_json_invalid() {
        String json = "{\"swagger\": \"2.0\"}";

        Assertions.assertThrows(CustomException.class, () -> SwaggerUtils.parseSwaggerJson(json),
                Messages.INVALID_SWAGGER_JSON);
    }

    private String jsonString() {
        return "{\n" +
                "  \"swagger\": \"2.0\",\n" +
                "  \"info\": {\n" +
                "    \"description\": \"This is a order aggregation server.\",\n" +
                "    \"version\": \"" + VERSION + "\",\n" +
                "    \"title\": \"" + TITLE + "\",\n" +
                "    \"contact\": {\n" +
                "      \"email\": \"syxiao@thoughtworks.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"host\": \"estore-dev-bce.mercedes-benz.com.cn\",\n" +
                "  \"basePath\": \"/order-aggregation-service\",\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"name\": \"App\",\n" +
                "      \"description\": \"about application platform\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"store\",\n" +
                "      \"description\": \"Access to Petstore orders\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"user\",\n" +
                "      \"description\": \"Operations about user\",\n" +
                "      \"externalDocs\": {\n" +
                "        \"description\": \"Find out more about our store\",\n" +
                "        \"url\": \"http://swagger.io\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"schemes\": [\n" +
                "    \"https\"\n" +
                "  ],\n" +
                "  \"paths\": {\n" +
                "    \"/api/app\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"App\"\n" +
                "        ],\n" +
                "        \"summary\": \"registry a application into server\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"addApp\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Pet object that needs to be added to the store\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Invalid input\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"put\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Update an existing pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"updatePet\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/json\",\n" +
                "          \"application/xml\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Pet object that needs to be added to the store\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          },\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Validation exception\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/findByStatus\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Finds Pets by status\",\n" +
                "        \"description\": \"Multiple status values can be provided with comma separated strings\",\n" +
                "        \"operationId\": \"findPetsByStatus\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"status\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"Status values that need to be considered for filter\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "              \"type\": \"string\",\n" +
                "              \"enum\": [\n" +
                "                \"available\",\n" +
                "                \"pending\",\n" +
                "                \"sold\"\n" +
                "              ],\n" +
                "              \"default\": \"available\"\n" +
                "            },\n" +
                "            \"collectionFormat\": \"multi\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/Pet\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid status value\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/findByTags\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Finds Pets by tags\",\n" +
                "        \"description\": \"Muliple tags can be provided with comma separated strings. Use         tag1, tag2, tag3 for testing.\",\n" +
                "        \"operationId\": \"findPetsByTags\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"tags\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"Tags to filter by\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            \"collectionFormat\": \"multi\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/Pet\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid tag value\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ],\n" +
                "        \"deprecated\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/{petId}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Find pet by ID\",\n" +
                "        \"description\": \"Returns a single pet\",\n" +
                "        \"operationId\": \"getPetById\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet to return\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Pet\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"api_key\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Updates a pet in the store with form data\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"updatePetWithForm\",\n" +
                "        \"consumes\": [\n" +
                "          \"application/x-www-form-urlencoded\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet that needs to be updated\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"name\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Updated name of the pet\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"status\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Updated status of the pet\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"405\": {\n" +
                "            \"description\": \"Invalid input\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"Deletes a pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"deletePet\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"api_key\",\n" +
                "            \"in\": \"header\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"Pet id to delete\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Pet not found\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pet/{petId}/uploadImage\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"pet\"\n" +
                "        ],\n" +
                "        \"summary\": \"uploads an image\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"uploadFile\",\n" +
                "        \"consumes\": [\n" +
                "          \"multipart/form-data\"\n" +
                "        ],\n" +
                "        \"produces\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"petId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet to update\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"format\": \"int64\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"additionalMetadata\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"Additional data to pass to server\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"file\",\n" +
                "            \"in\": \"formData\",\n" +
                "            \"description\": \"file to upload\",\n" +
                "            \"required\": false,\n" +
                "            \"type\": \"file\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/ApiResponse\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"petstore_auth\": [\n" +
                "              \"write:pets\",\n" +
                "              \"read:pets\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/inventory\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Returns pet inventories by status\",\n" +
                "        \"description\": \"Returns a map of status codes to quantities\",\n" +
                "        \"operationId\": \"getInventory\",\n" +
                "        \"produces\": [\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"object\",\n" +
                "              \"additionalProperties\": {\n" +
                "                \"type\": \"integer\",\n" +
                "                \"format\": \"int32\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": [\n" +
                "          {\n" +
                "            \"api_key\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/order\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Place an order for a pet\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"placeOrder\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"order placed for purchasing the pet\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid Order\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/store/order/{orderId}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Find purchase order by ID\",\n" +
                "        \"description\": \"For valid response try integer IDs with value >= 1 and <= 10.         Other values will generated exceptions\",\n" +
                "        \"operationId\": \"getOrderById\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"orderId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of pet that needs to be fetched\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"maximum\": 10,\n" +
                "            \"minimum\": 1,\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/Order\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Order not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"store\"\n" +
                "        ],\n" +
                "        \"summary\": \"Delete purchase order by ID\",\n" +
                "        \"description\": \"For valid response try integer IDs with positive integer value.         Negative or non-integer values will generate API errors\",\n" +
                "        \"operationId\": \"deleteOrder\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"orderId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"ID of the order that needs to be deleted\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"integer\",\n" +
                "            \"minimum\": 1,\n" +
                "            \"format\": \"int64\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid ID supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"Order not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Create user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"createUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Created user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/createWithArray\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Creates list of users with given input array\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"createUsersWithArrayInput\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"List of user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/User\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/createWithList\": {\n" +
                "      \"post\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Creates list of users with given input array\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"createUsersWithListInput\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"List of user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"array\",\n" +
                "              \"items\": {\n" +
                "                \"$ref\": \"#/definitions/User\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/login\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Logs user into the system\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"loginUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"The user name for login\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"password\",\n" +
                "            \"in\": \"query\",\n" +
                "            \"description\": \"The password for login in clear text\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"string\"\n" +
                "            },\n" +
                "            \"headers\": {\n" +
                "              \"X-Rate-Limit\": {\n" +
                "                \"type\": \"integer\",\n" +
                "                \"format\": \"int32\",\n" +
                "                \"description\": \"calls per hour allowed by the user\"\n" +
                "              },\n" +
                "              \"X-Expires-After\": {\n" +
                "                \"type\": \"string\",\n" +
                "                \"format\": \"date-time\",\n" +
                "                \"description\": \"date in UTC when token expires\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username/password supplied\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/logout\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Logs out current logged in user session\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"logoutUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [],\n" +
                "        \"responses\": {\n" +
                "          \"default\": {\n" +
                "            \"description\": \"successful operation\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/user/{username}\": {\n" +
                "      \"get\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Get user by user name\",\n" +
                "        \"description\": \"\",\n" +
                "        \"operationId\": \"getUserByName\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"The name that needs to be fetched. Use user1 for testing. \",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"successful operation\",\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"put\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Updated user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"updateUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"name that need to be updated\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"in\": \"body\",\n" +
                "            \"name\": \"body\",\n" +
                "            \"description\": \"Updated user object\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"$ref\": \"#/definitions/User\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid user supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"delete\": {\n" +
                "        \"tags\": [\n" +
                "          \"user\"\n" +
                "        ],\n" +
                "        \"summary\": \"Delete user\",\n" +
                "        \"description\": \"This can only be done by the logged in user.\",\n" +
                "        \"operationId\": \"deleteUser\",\n" +
                "        \"produces\": [\n" +
                "          \"application/xml\",\n" +
                "          \"application/json\"\n" +
                "        ],\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"username\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"description\": \"The name that needs to be deleted\",\n" +
                "            \"required\": true,\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"400\": {\n" +
                "            \"description\": \"Invalid username supplied\"\n" +
                "          },\n" +
                "          \"404\": {\n" +
                "            \"description\": \"User not found\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"securityDefinitions\": {\n" +
                "    \"petstore_auth\": {\n" +
                "      \"type\": \"oauth2\",\n" +
                "      \"authorizationUrl\": \"http://petstore.swagger.io/oauth/dialog\",\n" +
                "      \"flow\": \"implicit\",\n" +
                "      \"scopes\": {\n" +
                "        \"write:pets\": \"modify pets in your account\",\n" +
                "        \"read:pets\": \"read your pets\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"api_key\": {\n" +
                "      \"type\": \"apiKey\",\n" +
                "      \"name\": \"api_key\",\n" +
                "      \"in\": \"header\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"definitions\": {\n" +
                "    \"Order\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"petId\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"quantity\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\"\n" +
                "        },\n" +
                "        \"shipDate\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"format\": \"date-time\"\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"description\": \"Order Status\",\n" +
                "          \"enum\": [\n" +
                "            \"placed\",\n" +
                "            \"approved\",\n" +
                "            \"delivered\"\n" +
                "          ]\n" +
                "        },\n" +
                "        \"complete\": {\n" +
                "          \"type\": \"boolean\",\n" +
                "          \"default\": false\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Order\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Category\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Category\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"User\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"username\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"firstName\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"lastName\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"email\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"password\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"phone\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"userStatus\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\",\n" +
                "          \"description\": \"User Status\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"User\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Tag\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Tag\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"Pet\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"required\": [\n" +
                "        \"name\",\n" +
                "        \"photoUrls\"\n" +
                "      ],\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int64\"\n" +
                "        },\n" +
                "        \"category\": {\n" +
                "          \"$ref\": \"#/definitions/Category\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"example\": \"doggie\"\n" +
                "        },\n" +
                "        \"photoUrls\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"xml\": {\n" +
                "            \"name\": \"photoUrl\",\n" +
                "            \"wrapped\": true\n" +
                "          },\n" +
                "          \"items\": {\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"tags\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"xml\": {\n" +
                "            \"name\": \"tag\",\n" +
                "            \"wrapped\": true\n" +
                "          },\n" +
                "          \"items\": {\n" +
                "            \"$ref\": \"#/definitions/Tag\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"description\": \"pet status in the store\",\n" +
                "          \"enum\": [\n" +
                "            \"available\",\n" +
                "            \"pending\",\n" +
                "            \"sold\"\n" +
                "          ]\n" +
                "        }\n" +
                "      },\n" +
                "      \"xml\": {\n" +
                "        \"name\": \"Pet\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"ApiResponse\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"code\": {\n" +
                "          \"type\": \"integer\",\n" +
                "          \"format\": \"int32\"\n" +
                "        },\n" +
                "        \"type\": {\n" +
                "          \"type\": \"string\"\n" +
                "        },\n" +
                "        \"message\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"externalDocs\": {\n" +
                "    \"description\": \"Find out more about Swagger\",\n" +
                "    \"url\": \"http://swagger.io\"\n" +
                "  }\n" +
                "}";
    }
}
