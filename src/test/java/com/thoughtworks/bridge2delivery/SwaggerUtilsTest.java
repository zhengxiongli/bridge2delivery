package com.thoughtworks.bridge2delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.bridge2delivery.swagger.SwaggerUtils;

public class SwaggerUtilsTest {

    @org.junit.jupiter.api.Test
    public void test() throws JsonProcessingException {
        //String json = "{\"swagger\":\"2.0\",\"info\":{\"description\":\"API接口文档\",\"version\":\"1.1.0\",\"title\":\"嘀嗒交付小程序swagger测试程序\"},\"host\":\"localhost:8081\",\"basePath\":\"/\",\"tags\":[{\"name\":\"测试类标签\",\"description\":\"测试接口类\"},{\"name\":\"示列接口tag\",\"description\":\"示列接口\"}],\"paths\":{\"/demo/list\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"list\",\"description\":\"data类型为list\",\"operationId\":\"listUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"pageIndex\",\"in\":\"query\",\"description\":\"页码\",\"required\":false,\"type\":\"integer\",\"default\":1,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"pageSize\",\"in\":\"query\",\"description\":\"每页显示条数\",\"required\":false,\"type\":\"integer\",\"default\":15,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"param\",\"in\":\"query\",\"description\":\"param\",\"required\":false,\"type\":\"integer\",\"format\":\"int32\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«List«Result»»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/list/list\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"list\",\"description\":\"两个list\",\"operationId\":\"listListUsingGET\",\"consumes\":[\"multipart/form-data\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"file\",\"in\":\"formData\",\"description\":\"file\",\"required\":true,\"type\":\"file\"},{\"name\":\"param\",\"in\":\"query\",\"description\":\"param\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«List«List«Result»»»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/list/request\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"list request\",\"operationId\":\"listRequestUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"in\":\"body\",\"name\":\"requests\",\"description\":\"requests\",\"required\":true,\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/Request\"}}},{\"in\":\"body\",\"name\":\"strings\",\"description\":\"strings\",\"required\":true,\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/list2\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"list2\",\"description\":\"直接返回list\",\"operationId\":\"list2UsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"pageIndex\",\"in\":\"query\",\"description\":\"页码\",\"required\":false,\"type\":\"integer\",\"default\":1,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"pageSize\",\"in\":\"query\",\"description\":\"每页显示条数\",\"required\":false,\"type\":\"integer\",\"default\":15,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"param\",\"in\":\"query\",\"description\":\"param\",\"required\":false,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/Result\"}}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/pageresult\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"pageresult\",\"description\":\"data类型为PageResult\",\"operationId\":\"pageResultUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"pageIndex\",\"in\":\"query\",\"required\":false,\"type\":\"integer\",\"format\":\"int32\"},{\"name\":\"pageSize\",\"in\":\"query\",\"required\":false,\"type\":\"integer\",\"format\":\"int32\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«PageResult«Result»»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false},\"post\":{\"tags\":[\"示列接口tag\"],\"summary\":\"pageresult post\",\"description\":\"post page result\",\"operationId\":\"postUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«PageResult«Result»»\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/path/{path}\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"path\",\"description\":\"带路径参数API\",\"operationId\":\"pathUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"pageIndex\",\"in\":\"query\",\"description\":\"页码\",\"required\":false,\"type\":\"integer\",\"default\":1,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"pageSize\",\"in\":\"query\",\"description\":\"每页显示条数\",\"required\":false,\"type\":\"integer\",\"default\":15,\"format\":\"int32\",\"allowEmptyValue\":false},{\"name\":\"param\",\"in\":\"query\",\"description\":\"param\",\"required\":false,\"type\":\"string\"},{\"name\":\"path\",\"in\":\"path\",\"description\":\"路径参数\",\"required\":false,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«List«Result»»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/request\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"request\",\"description\":\"带参数\",\"operationId\":\"requestUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"in\":\"body\",\"name\":\"request\",\"description\":\"json请求\",\"required\":false,\"schema\":{\"$ref\":\"#/definitions/Request\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/string\":{\"get\":{\"tags\":[\"示列接口tag\"],\"summary\":\"string\",\"description\":\"data类型为String\",\"operationId\":\"stringUsingGET\",\"produces\":[\"*/*\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse«int»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/demo/upload\":{\"post\":{\"tags\":[\"示列接口tag\"],\"summary\":\"upload\",\"description\":\"上传文件\",\"operationId\":\"uploadUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ApiResponse\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/test\":{\"get\":{\"tags\":[\"测试类标签\"],\"summary\":\"测试接口\",\"description\":\"测试接口notes\",\"operationId\":\"test1UsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"param1\",\"in\":\"query\",\"description\":\"参数1\",\"required\":false,\"type\":\"integer\",\"default\":0,\"format\":\"int32\",\"allowEmptyValue\":false}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"string\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}},\"/test/2\":{\"get\":{\"tags\":[\"测试类标签\"],\"summary\":\"测试接口2\",\"description\":\"测试接口2notes\",\"operationId\":\"test2UsingGET\",\"produces\":[\"*/*\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"string\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}}},\"definitions\":{\"ApiResponse\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"description\":\"返回数据结构\"},\"errCode\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":404,\"description\":\"自定义错误code\"},\"message\":{\"type\":\"string\",\"example\":\"not found\",\"description\":\"错误消息\"}},\"title\":\"ApiResponse\",\"description\":\"统一返回数据结构\"},\"ApiResponse«List«List«Result»»»\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"description\":\"返回数据结构\",\"items\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/Result\"}}},\"errCode\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":404,\"description\":\"自定义错误code\"},\"message\":{\"type\":\"string\",\"example\":\"not found\",\"description\":\"错误消息\"}},\"title\":\"ApiResponse«List«List«Result»»»\",\"description\":\"统一返回数据结构\"},\"ApiResponse«List«Result»»\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"description\":\"返回数据结构\",\"items\":{\"$ref\":\"#/definitions/Result\"}},\"errCode\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":404,\"description\":\"自定义错误code\"},\"message\":{\"type\":\"string\",\"example\":\"not found\",\"description\":\"错误消息\"}},\"title\":\"ApiResponse«List«Result»»\",\"description\":\"统一返回数据结构\"},\"ApiResponse«PageResult«Result»»\":{\"type\":\"object\",\"properties\":{\"data\":{\"description\":\"返回数据结构\",\"$ref\":\"#/definitions/PageResult«Result»\"},\"errCode\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":404,\"description\":\"自定义错误code\"},\"message\":{\"type\":\"string\",\"example\":\"not found\",\"description\":\"错误消息\"}},\"title\":\"ApiResponse«PageResult«Result»»\",\"description\":\"统一返回数据结构\"},\"ApiResponse«int»\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"返回数据结构\"},\"errCode\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":404,\"description\":\"自定义错误code\"},\"message\":{\"type\":\"string\",\"example\":\"not found\",\"description\":\"错误消息\"}},\"title\":\"ApiResponse«int»\",\"description\":\"统一返回数据结构\"},\"PageResult«Result»\":{\"type\":\"object\",\"properties\":{\"results\":{\"type\":\"array\",\"description\":\"结果集\",\"items\":{\"$ref\":\"#/definitions/Result\"}},\"total\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"总条数\"}},\"title\":\"PageResult«Result»\",\"description\":\"分页结果返回值\"},\"Request\":{\"type\":\"object\",\"properties\":{\"param1\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"请求参数1\"},\"param2\":{\"type\":\"string\",\"description\":\"请求参数2\"},\"subRequest\":{\"description\":\"子类\",\"$ref\":\"#/definitions/SubRequest\"}},\"title\":\"Request\",\"description\":\"请求类\"},\"Result\":{\"type\":\"object\",\"properties\":{\"field1\":{\"type\":\"string\",\"example\":\"string\",\"description\":\"返回字段1\"},\"field2\":{\"type\":\"integer\",\"format\":\"int32\",\"example\":\"string2\",\"description\":\"返回字段2\"},\"value\":{\"type\":\"string\",\"example\":\"enum\",\"description\":\"返回字段3\",\"enum\":[\"VALUE1\",\"VALUE2\"]}},\"title\":\"Result\",\"description\":\"返回结果数据结构\"},\"SubRequest\":{\"type\":\"object\",\"properties\":{\"param1\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"参数1\"},\"param2\":{\"type\":\"string\",\"description\":\"参数2\"}},\"title\":\"SubRequest\",\"description\":\"请求子类\"}}}";
        String json = "{\n" +
                "  \"swagger\": \"2.0\",\n" +
                "  \"info\": {\n" +
                "    \"description\": \"This is a order aggregation server.\",\n" +
                "    \"version\": \"1.0.0\",\n" +
                "    \"title\": \"Order Aggregation Server\",\n" +
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
        SwaggerUtils.parseSwaggerJson(json);
    }
}
