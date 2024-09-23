
# Workflow Modifier Task

*Script based task to update the existing workflows and add async to below mentioned tasks.*


### Wokflow Tasks list

1. ``` propertyManagedTask.setBooleanVlue ```
2. ``` propertyManagedTask.setCounterValue ```



## Prerequisite
* Copy the Workflow.zip file to your system. [Anywhere in the system from where command propt can be opened with admin rights].
* Extract the folder.

#### File list content
1. Item 1
2. Item 2
3. Item 3


## Usage/Examples

```javascript
1. Access the application UI by login with your credentials.
2. Navigate to Data Admin Page [ Administration > Data Admin ]
3. Inspect the page => Right click on page > Inspect
4. Navigate to networks tab from the top.
5. Now click on Dispatch Workflows in the application UI.
6. Look for api [http:127.0.0.1/workflows].
7. Right click on the API Call and copy the "curl cmd".
8. Now navigate to the path where you have extracted the provided .zip folder.
9. Open the command prompt from the same location.
10. Path the "curl cmd" copied in STEP '7'
11. Now add "-o workflow.json --insecure"
12. Press ENTER and run the command.
13. workflow.json will be created in the same location.
14. Now run another command in the same location on cmd 
> java - jar something.jar 
15. Another file will be created "workflow_new.json"
```

#### Postman setup
```
1. Open Postman application.
2. Continue with light-weight-client application.
3. Import the postman collection & environment

- collection.json
- env.json

Once Imported
1. Put the SERVER-IP ADDRESS of your application
2. Put the Access-Token in of you user [User token Which you have logged in with].
```
#### Running the Postman collection.
```
1. Run the collection.
2. Select the data file which we have created
```