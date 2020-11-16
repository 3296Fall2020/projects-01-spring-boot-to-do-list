import React, { useContext, useState, useEffect } from 'react';
import { Context } from '../context'
import DeleteList from './deleteList'
import AddListUser from './addUserToList'
import './listContent.css'

export default function ListContent() {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);
    const [update, setUpdate] = useState(false);
    const [updateName, setUpdateName] = useState("");
    const [showDeleteList, setShowDeleteList] = useState(false);
    const [showAddUser, setShowAddUser] = useState(false);

    
    const removeUser = (user) => {
        let url = 'http://localhost:8080/list/remove?user_id=' + user.id + '&list_id=' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchListUsers(list);
        })
            .catch((exception) => {
                console.log(exception);
            });
    }

    const updateList = e => {
        e.preventDefault();
        console.log(updateName);
        let data = { "list_name": updateName };
        let url = 'http://localhost:8080/list/update/' + list.id;
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(data)
        }).then((response) => {
            fetchList(list.id);
            setUpdateName("");
            setUpdate(false);
        }).catch((exception) => {
            console.log(exception);
        })
    }

    const handleDeleteListModal = () => {
        setShowDeleteList(!showDeleteList);
    }

    const handleAddUserModal = () => {
        setShowAddUser(!showAddUser);
    }

    if (list.list_name != null) {
        return (
            <div className="list_content">
                <button className="update_list_button" onClick={() => setUpdate(true)}>Update list</button>
                <span className="list_header" style={{ display: update ? "none" : "block" }}>{list.list_name}</span>
                <form className="update_list_form" onSubmit={updateList} style={{ display: update ? "block" : "none" }}>
                    <button type="button" onClick={() => { setUpdate(false); setUpdateName("") }}>&times;</button>
                    <input value={updateName} onChange={e => setUpdateName(e.target.value)} className="form-control" placeholder="New List Name" required="required" ></input>
                </form>
                <button className="delete_list_button" onClick={handleDeleteListModal}>Delete List</button>
                <div className="members">
                    {listUsers.map(user => {
                        return (
                            <span className="list_member" key={user.id}>
                                <span className="list_member_name">{user.first_name}&nbsp;{user.last_name}</span>
                                <span className="remove_user_button" onClick={() => removeUser(user)}>&#10005;</span>
                            </span>
                        )
                    })}
                    <button className="add_user_button" onClick={handleAddUserModal}>Add Member</button>
                </div>
                <DeleteList show={showDeleteList} close={handleDeleteListModal} />
                <AddListUser show={showAddUser} close={handleAddUserModal} />
            </div>
        );
    } else {
        return (
            <div className="list_content">
                <h1>No Lists Available</h1>
            </div>
        );
    }
}