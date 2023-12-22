## Setup

### Variables

In order to use this product you must configure multiple variables.

Add the following block to your `config/variables.yaml` file of our 
main Business Project that will make use of this product:

```
@variables.yaml@ 
```

Afterwards set the Consumer key, Consumer Secret, and App domain as shown in the Salesforce App setup below.

Example App domain: axonactive3-dev-ed.develop


### Salesforce App

1. Create the Connected App so that can generate the Authentication information

![plot](/salesforce-connector-product/doc/img/sf-connected-app-01.png)

![plot](/salesforce-connector-product/doc/img/sf-connected-app-02.png)

2. Set up Auth. Providers

![plot](/salesforce-connector-product/doc/img/sf-oauth-providers.png)

3. Enable some settings for OAuth2.0

![plot](/salesforce-connector-product/doc/img/sf-oauth-settings.png)

3. Set up Named Credentials

![plot](/salesforce-connector-product/doc/img/sf-name-credentials.png)
