[![JUnit Tests](https://github.com/BradTheeStallion/DataStructuresAlgorithms_FinalSprint/actions/workflows/ci.yml/badge.svg)](https://github.com/BradTheeStallion/DataStructuresAlgorithms_FinalSprint/actions/workflows/ci.yml)

[![Deploy to EC2](https://github.com/BradTheeStallion/DataStructuresAlgorithms_FinalSprint/actions/workflows/cd.yml/badge.svg)](https://github.com/BradTheeStallion/DataStructuresAlgorithms_FinalSprint/actions/workflows/cd.yml)

# Binary Search Trees
## Final Sprint Project for Data Structures and Algorithms
The objective of this project was to develop a Spring Boot application that allows users to create a binary search tree from a series of numbers, visualize the resulting tree, and view previous tree results.
The backend API was built using Java with Spring Boot, and the frontend was developed using React. 
A MySQL database was used to manage all application data.
The entire project was containerized with Docker, thoroughly tested using a combination of unit and manual testing, and deployed to AWS using EC2.

Feel free to read the assignment [here](https://github.com/user-attachments/files/19784294/DSA.Winter.2025.Final.pdf).

The application is currently not configured to run locally, but feel free to read through the backend code in this project, the frontend code [here](https://github.com/BradTheeStallion/binary-search-tree), and check out the deployed project [here](http://brad-thee-stallion-portfolio.s3-website-us-east-1.amazonaws.com/).

## 🔍 Project Summary
## Binary Search Tree API Guide

This API allows you to create, retrieve, and delete binary search trees. The API manages trees created from arrays of values and provides operations to interact with these trees.

Please note that the JSONs below are indicative of what will display on a Postman test and the React app displays a tidier version of the JSON.

### Create a New Tree

Creates a new binary search tree with the provided name and values.

- **URL**: `/api/trees`
- **Method**: `POST`
- **Content-Type**: `application/json`

#### Request Body

| Field  | Type     | Description                                     | Required |
|--------|----------|-------------------------------------------------|----------|
| name   | String   | A name for the binary search tree               | Yes      |
| values | Integer[] | Array of integer values to build the tree from | Yes      |

#### Example Request

<pre>
{
    "name": "Test Tree 1",
    "values": [50, 30, 70, 90, 40, 60, 80]
}
</pre>

#### Response

- **Status Code**: `201 Created`
- **Content-Type**: `application/json`

#### Example Response

<pre>
{
  "id": 1,
  "name": "Test Tree 1",
  "createdAt": "2025-04-16T20:47:22.765505953",
  "originalInputs": [50, 30, 70, 90, 40, 60, 80],
  "nodeCount": 7,
  "height": 3,
  "isBalanced": true,
  "rootNode": {
    "id": 84,
    "value": 50,
    "left": {
      "id": 85,
      "value": 30,
      "left": null,
      "right": {
        "id": 86,
        "value": 40,
        "left": null,
        "right": null,
        "leaf": true
      },
      "leaf": false
    },
    "right": {
      "id": 87,
      "value": 70,
      "left": {
        "id": 88,
        "value": 60,
        "left": null,
        "right": null,
        "leaf": true
      },
      "right": {
        "id": 89,
        "value": 90,
        "left": {
          "id": 90,
          "value": 80,
          "left": null,
          "right": null,
          "leaf": true
        },
        "right": null,
        "leaf": false
      },
      "leaf": false
    },
    "leaf": false
  }
}
</pre>

### Get a Specific Tree

Retrieves a specific binary search tree by its ID.

- **URL**: `/api/trees/{id}`
- **Method**: `GET`
- **URL Parameters**: `id=[Long]`

#### Example Request

```
GET /api/trees/1
```

#### Response

- **Status Code**: `200 OK`
- **Content-Type**: `application/json`

#### Example Response

<pre>
{
  "id": 1,
  "name": "Test Tree 1",
  "createdAt": "2025-04-16T20:47:22.765505953",
  "originalInputs": [50, 30, 70, 90, 40, 60, 80],
  "nodeCount": 7,
  "height": 3,
  "isBalanced": true,
  "rootNode": {
    "id": 84,
    "value": 50,
    "left": {
      "id": 85,
      "value": 30,
      "left": null,
      "right": {
        "id": 86,
        "value": 40,
        "left": null,
        "right": null,
        "leaf": true
      },
      "leaf": false
    },
    "right": {
      "id": 87,
      "value": 70,
      "left": {
        "id": 88,
        "value": 60,
        "left": null,
        "right": null,
        "leaf": true
      },
      "right": {
        "id": 89,
        "value": 90,
        "left": {
          "id": 90,
          "value": 80,
          "left": null,
          "right": null,
          "leaf": true
        },
        "right": null,
        "leaf": false
      },
      "leaf": false
    },
    "leaf": false
  }
}
</pre>

### Get All Trees

Retrieves a paginated list of all binary search trees.

- **URL**: `/api/trees`
- **Method**: `GET`
- **Query Parameters**:
  - `page=[Integer]` - Page number (default: 0)
  - `size=[Integer]` - Number of items per page (default: 10)

#### Example Request

```
GET /api/trees
```

#### Response

- **Status Code**: `200 OK`
- **Content-Type**: `application/json`

#### Example Response

<pre>
{
  "trees": [
    {
      "id": 1,
      "name": "Test Tree 1",
      "createdAt": "2025-04-16T20:47:22.765506",
      "nodeCount": 7,
      "height": 3,
      "isBalanced": true
    },
    {
      "id": 2,
      "name": "test",
      "createdAt": "2025-04-16T19:25:21.749214",
      "nodeCount": 7,
      "height": 5,
      "isBalanced": false
    },
    {
      "id": 3,
      "name": "uhjfv",
      "createdAt": "2025-04-16T19:16:28.762452",
      "nodeCount": 6,
      "height": 4,
      "isBalanced": false
    },
    {
      "id": 4,
      "name": "Manual Test",
      "createdAt": "2025-04-16T18:43:07.804892",
      "nodeCount": 6,
      "height": 4,
      "isBalanced": false
    }
  ],
  "totalCount": 4,
  "page": 0,
  "size": 5
}
</pre>

### Delete a Tree

Deletes a specific binary search tree by its ID.

- **URL**: `/api/trees/{id}`
- **Method**: `DELETE`
- **URL Parameters**: `id=[Long]`

#### Example Request

```
DELETE /api/trees/1
```

#### Response

- **Status Code**: `204 No Content`
