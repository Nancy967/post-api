# post-api

- Get post by post ID
- Get posts
- Create post
- Update post
- Delete post

## Get post

`GET` `http://localhost:8080/posts/{postId}`

_Request example_

```shell
curl -v -X GET http://localhost:8080/posts/1
```

### Path parameter

| Name   | Type     | Description           |
| ------ | -------- |-----------------------|
| postId | `Number` | **Required.** Post ID |

### Response

`200 OK`

| Name             | Type     | Description             |
| ---------------- | -------- | ----------------------- |
| postId           | `Number` | Post ID                 |
| title            | `String` | Post title              |
| imageUrl         | `String` | Post image url          |
| description      | `String` | Post descroption        |
| createdDate      | `String` | Post created date       |
| lastModifiedDate | `String` | Post last modified date |

_Response example_

```json
{
  "postId": 1,
  "title": "New Seasonal Delights",
  "imageUrl": "https://i0.wp.com/macaudailytimes.com.mo/wp-content/uploads/2023/08/Whey-Summer-Menu-Group-Photo-1.jpg?w=670&ssl=1",
  "description": "Introducing our latest seasonal menu filled with fresh, locally sourced ingredients that celebrate the flavors of the season.",
  "createdDate": "2023-10-06 10:00:00",
  "lastModifiedDate": "2023-10-06 10:00:00"
}
```

### Error response

`404 Not Found`

## Get list of post

`GET` `http://localhost:8080/posts`

_Request example_

```shell
curl -v -X GET http://localhost:8080/posts \
--data-urlencode 'search=Night' \
--data-urlencode 'orderBy=created_date' \
--data-urlencode 'sort=asc' \
--data-urlencode 'limit=2' \
--data-urlencode 'offset=2' \
-G
```

### Query parameters

| Name    | Type     | Description                   |
| ------- | -------- |-------------------------------|
| search  | `String` | Search for post title         |
| orderBy | `String` | Default: `last_modified_date` |
| sort    | `String` | Default: `desc`               |
| limit   | `String` | Default: `9`. `Max: 1000`      |
| offset  | `String` | Default: `0`                  |

### Response

`200 OK`

| Name                     | Type     | Description              |
| ------------------------ | -------- | ------------------------ |
| limit                    | `Number` | Post limit per page      |
| offset                   | `Number` | Post offset              |
| total                    | `Number` | Post total               |
| results                  | `Array`  | Array of posts. `Max:1000` |
| results.postId           | `Number` | Post ID                  |
| results.title            | `String` | Post title               |
| results.imageUrl         | `String` | Post image url           |
| results.description      | `String` | Post descroption         |
| results.createdDate      | `String` | Post created date        |
| results.lastModifiedDate | `String` | Post last modified date  |

_Response example_

```json
{
  "limit": 2,
  "offset": 2,
  "total": 4,
  "results": [
    {
      "postId": 17,
      "title": "Trivia Night",
      "imageUrl": "https://cdn.cloudflare.steamstatic.com/steam/apps/628570/header.jpg?t=1652470286",
      "description": "Test your knowledge and win prizes at our weekly trivia night. Enjoy great food and brain-teasing questions.",
      "createdDate": "2023-10-06 23:00:00",
      "lastModifiedDate": "2023-10-06 23:00:00"
    },
    {
      "postId": 18,
      "title": "Late-Night Bites",
      "imageUrl": "https://content.mattressadvisor.com/wp-content/uploads/2018/08/late-night-fast-food-e1535060362940-1024x550.jpg",
      "description": "Craving a midnight snack? Our late-night menu is now available until 1 AM on weekends.",
      "createdDate": "2023-10-07 07:00:00",
      "lastModifiedDate": "2023-10-07 07:00:00"
    }
  ]
}
```

## Create post

`POST` `http://localhost:8080/posts`

_Request example_

```shell
curl -v -X POST http://localhost:8080/posts \
-H 'Content-Type: application/json' \
-d '{
  "title":"test",
  "imageUrl":null,
  "description":"hello test"
}'
```

### Request headers

| Name         | Value              |
| ------------ | ------------------ |
| Content-Type | `application/json` |

### Request body

| Name        | Type     | Description                    |
| ----------- | -------- |--------------------------------|
| title       | `String` | **Required.** Post title       |
| imageUrl    | `String` | Post image url                 |
| description | `String` | **Required.** Post descroption |

### Response

`201 CREATED`

| Name             | Type     | Description             |
| ---------------- | -------- | ----------------------- |
| postId           | `Number` | Post ID                 |
| title            | `String` | Post title              |
| imageUrl         | `String` | Post image url          |
| description      | `String` | Post descroption        |
| createdDate      | `String` | Post created date       |
| lastModifiedDate | `String` | Post last modified date |

_Response example_

```json
{
  "postId": 21,
  "title": "test",
  "imageUrl": null,
  "description": "hello test",
  "createdDate": "2023-10-10 10:15:00",
  "lastModifiedDate": "2023-10-10 10:15:00"
}
```

### Error response

`400 Bad Request`

## Update post

`PUT` `http://localhost:8080/posts/{postId}`

_Request example_

```shell
curl -v -X PUT http://localhost:8080/posts/21 \
-H 'Content-Type: application/json' \
-d '{
  "title":"test2",
  "imageUrl":null,
  "description":"hello test2"
}'
```

### Path parameter

| Name   | Type     | Description           |
| ------ | -------- |-----------------------|
| postId | `Number` | **Required.** Post ID |

### Request headers

| Name         | Value              |
| ------------ | ------------------ |
| Content-Type | `application/json` |

### Request body

| Name        | Type     | Description                    |
| ----------- | -------- |--------------------------------|
| title       | `String` | **Required.** Post title       |
| imageUrl    | `String` | Post image url                 |
| Description | `String` | **Required.** Post descroption |

### Response

`200 OK`

| Name             | Type     | description             |
| ---------------- | -------- | ----------------------- |
| postId           | `Number` | Post ID                 |
| title            | `String` | Post title              |
| imageUrl         | `String` | Post image url          |
| description      | `String` | Post descroption        |
| createdDate      | `String` | Post created date       |
| lastModifiedDate | `String` | Post last modified date |

_Response example_

```json
{
  "postId": 21,
  "title": "test2",
  "imageUrl": null,
  "description": "hello test2",
  "createdDate": "2023-10-10 10:15:00",
  "lastModifiedDate": "2023-10-10 10:26:00"
}
```

### Error response

`400 Bad Request`

`404 Not Found`

## Delete post

`DELETE` `http://localhost:8080/posts/{postId}`

_Request example_

```shell
curl -v -X DELETE http://localhost:8080/posts/21
```

### Path parameter

| Name   | Type     | Description           |
| ------ | -------- |-----------------------|
| postId | `Number` | **Required.** Post ID |

### Response

`204 No Content`
