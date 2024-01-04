# salesforce-connector
# Introduction
This connector builds the REST API and provides a means to access Salesforce's resources. A client can utilize the REST API to manipulate Salesforce (SF) objects. For instance, to perform **CRUD** operations on an Opportunity object or to query and retrieve data.

# Approach
Data is stored on the SF Application as SF Objects. To manipulate these SF objects, you can call the SF Rest API.

# Use case
For example, if we want to retrieve all the **Opportunity** SF Objects or perform operations such as Create, Read, Update, and Delete on that object, we can achieve this by calling the SF API. Subsequently, we can display a list of all Opportunities and manipulate each of them as needed.

# Configuration


  ## Configure on the SF Application
  For a client application to access REST API resources, it must be authorized as a trusted visitor. To implement this authorization, utilize a **Connected App** and employ the **OAuth 2.0** authorization flow.

  **Important steps**
  
    - Create a Connected App to generate the necessary authentication information.
    - Configure authentication providers for your application.
    - Activate the required settings in OAuth and OpenID Connect settings to facilitate secure authentication.
    - Set up Named Credentials to manage and secure the access to external services.


  ## Integrate Connector in the Project
  The **Consumer Key** and **Consumer Secret** are retrieved from the **Connected App** and then placed in the `variables.yaml` file.

  Authentication information is set up as a global variable and can be updated anytime.

  
  | Variable name                              | Note                                          |Example                                                                               |
  |--------------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------------------|
  |salesforce-connector.auth.subdomain         |The domain that Salesforce provides to consumers for authentication         |axonactive3-dev-ed.develop                                                            |
  |salesforce-connector.auth.clientId          |Consumer Id                                    |3MVG95mg0lk4bathQF4Z_F1GcZZPr8ztvo29c53HhwOXnCKBkP8LkxHnb5KlydXj3Oomw0VHsY3qdrM8lzU76 |
  |salesforce-connector.auth.clientSecret      |Consumer Secret                                |56C6CA448B49032828FE4C4DF16D1AF4804B8CC734E066B255A5B31A9895D9D8                      |
  |salesforce-connector.auth.useAppPermissions |OAuth2 Grantype                                |true (client_credentials)                                                             |
  |salesforce-connector.auth.url               |Salesforce Rest URI                            |https://axonactive3-dev-ed.develop.my.salesforce.com/services/data/v58.0              |


# How to run the Demo project

  1. The Demo app has defined the REST client named **SalesforceAPI**.
     
  3. The Demo app has created CRUD sub-processes for the Opportunity object, namely: AddOppService, GetOppService, UpdateOppService and DeleteOppService.
     
  5. For querying, the Connector has defined an API service called QueryService. Simply pass the query command to receive a list of data in the responses.
     
     > **Note:** Salesforce provides [Salesforce SOQL](https://developer.salesforce.com/docs/atlas.en-us.soql_sosl.meta/soql_sosl/sforce_api_calls_soql.htm) (Salesforce Object Query Language) to query all Salesforce objects. 


# Demo

1. Set up a subprocess to call the Query Service for retrieving Opportunity data:

![plot](/salesforce-connector-product/doc/img/sf-query.png)

2. A simple chart for statistical analysis:

![plot](/salesforce-connector-product/doc/img/sf-chart.png)

3. Opportunity Overview:

![plot](/salesforce-connector-product/doc/img/sf-list.png)

4. Add new Opportunity:

![plot](/salesforce-connector-product/doc/img/sf-addNew.png)  

5. Opportunity details:

![plot](/salesforce-connector-product/doc/img/sf-detail.png)   

6. Edit Opportunity:

![plot](/salesforce-connector-product/doc/img/sf-edit.png)

7. Delete Opportunity:

![plot](/salesforce-connector-product/doc/img/sf-delete.png)   


# Resources
[https://developer.salesforce.com/docs](https://developer.salesforce.com/docs)

[Connected App](https://help.salesforce.com/s/articleView?id=sf.connected_app_client_credentials_setup.htm&type=5)

[Salesforce SOQL](https://developer.salesforce.com/docs/atlas.en-us.soql_sosl.meta/soql_sosl/sforce_api_calls_soql.htm)


