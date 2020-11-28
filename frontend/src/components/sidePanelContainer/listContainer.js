import React, {useContext} from 'react';
import {Context} from '../context';
import "./sidePanel.css";

export default function ListContainer(){
    const {setList, filterResults, fetchListUsers} = useContext(Context);

    const handleClick = (list) => {
        setList(list);
        fetchListUsers(list);
    }

    if (filterResults.length > 0) {
    return(
        <div className="list_container" id='scrollbar' >
        {filterResults.map((list) => {
            return(
                <div className="list_name" key={list.id} onClick={() => handleClick(list)}>
                <h3 key={list.id} id={list.id}>{list.list_name}</h3>
              </div>
           )
        })} 
        </div>
    )
    } else {
        return(
            <div className="list_container">
                <span>¯\_(ツ)_/¯</span>
                <p>No lists could be found</p>
            </div>
        );
    }
}