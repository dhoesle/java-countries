package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    private CountryRepository countrepos;

    private List<Country> findCountry(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }
    // http:localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();


        countrepos.findAll().iterator().forEachRemaining(myList::add);


        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for (Country c : myList)
        {
            System.out.println(c);
        }

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountry(myList, c -> c.getName().toLowerCase().charAt(0) == letter);


        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> displayPopulation()
    {
        List<Country> myList = new ArrayList<>();


        countrepos.findAll().iterator().forEachRemaining(myList::add);


        double total = 0.0;
        for (Country c : myList)
        {
            total = total + c.getPopulation();
            System.out.println("Population " + total);
        }

        return new ResponseEntity<>("The Total Population is " + total, HttpStatus.OK);
    }

    // http://localhost:2019/population/min

    // http://localhost:2019/population/max

    // *** STRETCH GOAL ***

    // http://localhost:2019/population/median
}
