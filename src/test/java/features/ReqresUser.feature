Feature: Feature to Test User related scenarios.
  Description: This feature contains the scenarios related to Users service.

  @Resource
  Scenario: Validate whether 2 resource is present on Get Request on "https://reqres.in/api/unknown/2".
    Given Get the response of unknown resource on end point 2 by Get Verbs.
    When Extract the response and store in variables.
    Then Validate whether "fuchsia rose" name is appearing in Response.

  @PostApi
  Scenario: Create user with different details by using post API on "https://gorest.co.in/public-api/users".
    Given Hit the post api "https://gorest.co.in/public-api/users" using "Bearer"  token "X-YJbVxrlRM9JubL8ZoUBbKNuZoOZVmVsU_g" with below user details and verify that it returns "200" status code.
      | first_name | last_name   | gender | email                    | status |
      | Alok       | Shrivastava | male   | chitransh@rediffmail.com | active |
      | Sudarshan  | Patil       | male   | sudarshan@rediffmail.com | active |
      | Sumbe      | Rushikesh   | male   | sumbe@rediffmail.com     | active |

  @ExcelData
  Scenario: Create user with different details by using post API on "https://gorest.co.in/public-api/users".
    Given Hit the post api "https://gorest.co.in/public-api/users" using "Bearer"  token "X-YJbVxrlRM9JubL8ZoUBbKNuZoOZVmVsU_g" with user details stored in excel at "src/test/resources/testdata/ExcelData.xlsx" and sheet no 0 . Also verify that it returns "200" status code.
