# salesforce-connector
# Introduction
This Connector builds the Rest API that offers a way to access Salesforce's resources.

A client can be called the Rest API for manipulating the SF(Salesforce) Objects. For Instance, to do a **CRUD** Operation or query to retrieve data.

# Approach
Data is stored on the SF Application as SF Objects.

Call the SF Rest API  to manipulate the SF Objects.

# Use case
For example, We want to get all the **Opportunity** SF Object or want to Create, Read, Update, and Delete that Object.

After calling the SF API, We can show a List of all Opportunities and we can manipulate each of them.

# How to Configure

  ## Configure on the SF Application
  For a client application to access REST API resources, it must be authorized as a safe visitor.
  
  To implement this authorization, use a **Connected App** and an **OAuth 2.0** authorization flow.

  **Important steps**:
  
    - Create the Connected App that can generate the Authentication information
    - Set up Auth. Providers
    - Enable some settings in OAuth and OpenID Connect Settings
    - Set up Named Credentials


  ## Configure in the Connector project:
  We will get the **Consumer Key** and **Consumer Secret** from the **Connected App** and store them in the variables.yaml

  Now Authenticate's information becomes a global variable and we can change or update it anytime.

  
  | Variable name                              | Note                                          |Example                                                                               |
  |--------------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------------------|
  |salesforce-connector.auth.subdomain         |The domain that SF provide to consumer         |axonactive3-dev-ed.develop                                                            |
  |salesforce-connector.auth.clientId          |Consumer Id                                    |3MVG95mg0lk4bathQF4Z_F1GcZZPr8ztvo29c53HhwOXnCKBkP8LkxHnb5KlydXj3Oomw0VHsY3qdrM8lzU76 |
  |salesforce-connector.auth.clientSecret      |Consumer Secret                                |56C6CA448B49032828FE4C4DF16D1AF4804B8CC734E066B255A5B31A9895D9D8                      |
  |salesforce-connector.auth.useAppPermissions |OAuth2 Grantype                                |true (client_credentials)                                                             |
    
# How to use

  1. The connector app has defined the Rest Client called **SalesforceAPI**
     
  3. The connector app has created CRUD sub process call for Opportunity Object: AddOppService, GetOppService, UpdateOppService, DeleteOppService.
     
  5. For Query, the connector has defined a Service called **QueryService**. Just simply pass the query command to get the list of data responses.
     
     > **Note:** The Salesforce provides SOQL to query all the SF objects

# Demo

1. Query Service:

![plot](/salesforce-connector-demo/recources/demo1.png)

2. Chart bar:

![plot](/salesforce-connector-demo/recources/demo2.png)

3. List all Opportunities:

![plot](/salesforce-connector-demo/recources/demo3.png)
