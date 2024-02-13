<h1>GitApi 1</h1>

This application connects with https://api.github.com and proceed
some operations on RestApi.
First version can display all repositories with its all branches
with the last commit sha.
You have to only provide the existing username. 
If user doesn't exist or user exists but don't have any repos
the application returns code 404 with a
suitable message.

To run the application correct first you need to generate a
GitHub token in developer settings here=https://github.com/settings/tokens 
and paste it in application.properties 
to variable github.access.token=TOKEN. It will allow you to extend 
your rate_limit of api requests.

ENDPOINTS:<br>
http://localhost:8080/git/limit <br>
Returns the limit of requests that is up of provided token.
It is a good test, if your token is suitable response should be 
for example:

"resources": {
"core": {
"limit": 5000.....

http://localhost:8080/git/repos/{username} <br>
Returns GitMasterDto object that represents user's
repositories with them branches with last commit sha.
Example response:
```json
{
  "userName": "Karol",
  "repositories": [
    {
      "repoName": "ADFS",
      "branches": [
        {
          "name": "master",
          "commit": {
            "sha": "a52a3b070e3d9f7ff5a06e03408c7f01e9bde678"
          }
        }
      ]
    },
    {
      "repoName": "easyhadoop",
      "branches": [
        {
          "name": "develop",
          "commit": {
            "sha": "cf1b8ce70e48d5f6fd8a85dd69ee1aa4325651e6"
          }
        },
        {
          "name": "gh-pages",
          "commit": {
            "sha": "a13908b088512d97412898cef0a6a9862d8a4d6e"
          }
        }
      ]
    }
  ]
}
```
If user doesn't exist

```json
{
  "status": 404,
  "message": "Git user not found!"
}
```
If user doesn't have any repos
```json
{
  "status": 404,
  "message": "User6766 doesn't have any repos"
}
```

