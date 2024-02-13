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
  "userName": "Karlz",
  "repositories": [
    {
      "repoName": "bootswatch",
      "branches": {
        "master": "2caa868f12eae97416356d1e2e6e18491ee27351"
      }
    },
    {
      "repoName": "EPPlus",
      "branches": {
        "Conditional_Formatting": "65fbe98e9e3882e14d17ff1b541dcc091ca57336",
        "ProtectedRanges": "2fa821e032cd5f6b14e03acb54f37446dc60796b",
        "CoreChanges": "221e56f7234ddd48a29f3677814caa30a0ca0233",
        "AutoFitMaximumValue": "e4556af2fed964794926ba6ca97ab70f6d54f320"
      }
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

