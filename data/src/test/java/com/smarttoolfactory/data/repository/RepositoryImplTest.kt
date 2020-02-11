package com.smarttoolfactory.data.repository

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


/**

 * Repository test for getting RepoDTO's, storing them on DB and fetching them when required,
 * and deleting old that whenever new data is retrieved.
 *
 * There are 2 approaches to use Single Source of Truth
 * 1- Online-first: Check Web Service for data first, if there is an error occurs check for DB
 * 2- Offline-first: Check DB for data first, if it's empty fetch data from Web Service
 *
 */

/*

    Feature 1: Get Repo DTOs and return them as entities with online-first fashion

    Scenario 1- Success
    1- Fetch RepoDTOs from web service
    2- Map DTO to Entity
    3- Given data is retrieved save delete previous data
    4- Save current data to DB
    5- Retrieve data

    Scenario 2- Web Service returns 404 or 500 error but DB has same user data
    1- Given Exception returned from web service check DB
    2- Given data is available retrieve data from DB

     Scenario 3- Web Service returns 404 or 500 error and DB does not have any daya
    1- Given Exception returned from web service check DB
    2- Given DB has no data for current user return empty list

 */
 class RepositoryImplTest {


}