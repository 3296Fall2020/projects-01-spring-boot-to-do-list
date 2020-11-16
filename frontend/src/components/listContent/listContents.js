import React, {useContext, useState, useEffect } from 'react';
import {Context} from '../context'
import './listContent.css'

export default function ListContent(){
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers] = useContext(Context);
    const [update, setUpdate] = useState(false);
    const [updateName, setUpdateName] = useState(list.list_name);



    const removeUser = (user) => {
        console.log(user.id);
        console.log(list.id);
        let url = 'http://localhost:8080/list/remove?user_id=' + user.id + '&list_id=' + list.id;
        console.log(url);
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).catch((exception) => {
            console.log(exception);
        });
        fetchListUsers(list);
    }

    const deleteList = () => {
        console.log(list.id);
        let url = 'http://localhost:8080/list/deleteList/' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).catch((exception) => {
            console.log(exception);
        });
        fetchLists();
    }

    const updateList = e => {
        e.preventDefault();
        console.log(updateName);
        console.log("call to server to update name");
        setUpdate(false);
    }

    if (list.list_name != null) {
        return(
            <div className="list_content">
                <button className="update_list_button" onClick={() => setUpdate(true)}>Update list</button>
                <span className="list_header" style={{ display: update? "none" : "block" }}>{list.list_name}</span>
                <form onSubmit={updateList} style={{ display: update ? "block" : "none" }}><input value={updateName} onChange={e => setUpdateName(e.target.value)}  className="form-control" placeholder="list name" required="required" ></input></form>
                <button className="delete_list_button" onClick={deleteList}>Delete List</button>
                <div className="members">
                {listUsers.map(user => {
                    return(
                        <span className="list_member" key={user.id}>
                           <span className="list_member_name">{user.first_name}&nbsp;{user.last_name}</span>
                           <span className="remove_user_button" onClick={() => removeUser(user)}>&#10005;</span>
                        </span>
                    )
                })}
            <button className="add_user_button">Add Member</button>
            </div>
            </div>
        );
    } else {
        return(
            <div className="list_content">
                <h1>No List Selected</h1>
            </div>
        );
    }
}