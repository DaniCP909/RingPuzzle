# Ring puzzles

## Overview
This project was created to learn about API design with Spring using a simple puzzle game i created exploring the basics of JavaScript. This API has to manage the backend that would be used by two frotends, web and mobile, ideally implemented in React and React Native.
![image](https://github.com/user-attachments/assets/90484de8-9e13-4067-931e-1a2113a6f071)

## Project structure
The project is divided into three main parts:
   1. API/Backend: A RESTful APU built with Spring Boot that handles the game app.
   2. Database: to persist all the data (MySQL)
   3. Web and Mobile Frontend: aiming to implement them with React and React Native

## Features 
 - REST API: Provides endpoints for CRUD operations and game state
 - Authentication and authorization using JWT.
![arquitecture](https://github.com/user-attachments/assets/53f24dc1-a044-4ccd-89c1-b4c137cf6ad0)

## API Endopints

| Method | Endpoint                           | Description                                                                                             | Access         |
|--------|------------------------------------|---------------------------------------------------------------------------------------------------------|----------------|
| POST   | /signup                            | Register user                                                                                           | Public         |
| POST   | /login                             | Login user                                                                                              | Public         |
| GET    | /users/                            | Returns all users                                                                                       | Private        |
| GET    | /users/me                          | Returns logged user                                                                                     | Private        |
| GET    | /ringpuzzles/                      | Returns all puzzles                                                                                     | Private        |
| GET    | /ringpuzzle/:accessionNumber       | Returns as peciffic puzzle if user has solved it at any level or calls /random_ringpuzzle               | Public         |
| GET    | /random_ringpuzzle                 | Returns an unsolved random puzzle for logged users  and a random one for guests | Public                | Public         |
| POST   | /ringpuzzles/:accessionNumber/edit | Edits an specific puzzle (to store ring position or update difficulty level solved| Public              | Private        |
| POST   | /ringpuzzles/create                | To create a new puzzle                                                                                  | Private        |

> [!NOTE]
>  - There are basic operations for entities not implemented yet.
>  - Current solution for login seems to be a bad practice because I did a custom management for security.
>  - JWT should be managed by cookies
