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

# How to use

  ## Configure on the SF Application
  For a client application to access REST API resources, it must be authorized as a safe visitor.
  
  To implement this authorization, use a connected app and an OAuth 2.0 authorization flow.

  **Important steps**:
  
    - Create the Connected App that can generate the Authentication information
    - Set up Auth. Providers
    - Enable some settings in OAuth and OpenID Connect Settings
    - Set up Named Credentials


  ## Configure in the Connector project:
  We will get the **Consumer Key** and **Consumer Secret** from the **Connected App** and store them in the variables.yaml

  Now Authenticate's information becomes a global variable and we can change or update it anytime.

    
