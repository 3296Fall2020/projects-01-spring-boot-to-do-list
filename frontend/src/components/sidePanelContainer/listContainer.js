import React, {useContext} from 'react';
import {Context} from '../context';
import "./sidePanel.css";

export default function ListContainer(){
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers] = useContext(Context);

    const handleClick = (list) => {
        setList(list);
        fetchListUsers(list);
    }

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
}