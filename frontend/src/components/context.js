import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import {useHistory} from 'react-router-dom';

const Context = React.createContext([{}, () => {}]);

const ContextProvider = (props) => {
    const [list, setList] = useState();
    const [lists, setLists] = useState([]);
    const location = useLocation();
    const [user, setUser] = useState({});
    const history = useHistory();
 

    useEffect(() => {
        if(location.state === undefined){
            alert("invalid user");
            history.push('/');
          } else {
           alert("User is: " + location.state.email);
           setUser({'email':location.state.email, 'password': location.state.password});
          }
        fetchLists();
    }, [location]);
    

    const fetchLists = () => {
        console.log('fetch list');
        fetch('http://localhost:8080/user/get_lists?email=' + user.email)
        .then(res => res.json())
        .then(data => {
            setLists(data);
            setFilterResults(data);
        })
        .catch(err => {
            // unable to get notes
            throw err;
        });
    };

    // holds the notes that matched the filter
    const [filterResults, setFilterResults] = useState([]);

    // is called whenever filterBy is changed
    const filterLists = (filter) => {
        let results = [];
            results = lists.filter(value => 
                //setting filter to be on list.name
                value.name.toLowerCase().includes(filter.search.toLowerCase())
            );
        setFilterResults(results);
    };


    return(
        <Context.Provider value={[user, list, setList, filterLists, filterResults, fetchLists, lists]}>
            {props.children}
        </Context.Provider>
    );
};

export {Context, ContextProvider};