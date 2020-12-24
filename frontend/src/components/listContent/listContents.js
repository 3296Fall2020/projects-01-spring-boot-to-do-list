import React, { useContext, useState } from 'react';
import { Context } from '../context'
import DeleteList from './deleteList'
import AddListUser from './addUserToList'
import ListItems from '../itemContent/listItems'
import './listContent.css'

export default function ListContent() {
    const {user, list, listUsers, setList, fetchLists, fetchListUsers, fetchList} = useContext(Context);
    const [update, setUpdate] = useState(false);
    const [updateName, setUpdateName] = useState("");
    const [showDeleteList, setShowDeleteList] = useState(false);
    const [showAddUser, setShowAddUser] = useState(false);


    const removeUser = (listUser) => {
        document.body.style.cursor='wait';
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/list/remove?user_id=' + listUser.id + '&list_id=' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchLists();
            fetchListUsers(list);
            if (listUser.id === user.id) {
                setList({ "list_id": list.id, "list_name": null })
            }
            document.body.style.cursor='default';
        })
            .catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("could not remove user, please try again");
            });
    }

    const updateList = e => {
        document.body.style.cursor='wait';
        e.preventDefault();
        console.log(updateName);
        let data = { "list_name": updateName };
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/list/update/' + list.id;
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(data)
        }).then((response) => {
            fetchLists();
            fetchList(list.id);
            document.body.style.cursor='default';
            setUpdate(false);
        }).catch((exception) => {
            document.body.style.cursor='default';
            console.log(exception);
            alert("could not update list, please try again");
            setUpdate(false);
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
                <div className="header">
                    <button className="update_list_button" onClick={() => { setUpdate(true); setUpdateName(list.list_name) }}>Update list</button>
                    <span className="header_name" style={{ display: update ? "none" : "block" }}>{list.list_name}</span>
                    <form className="update_list_form" onSubmit={updateList} style={{ display: update ? "block" : "none" }}>
                        <span className="update_list_close_button" onClick={() => { setUpdate(false) }}>&times;</span>
                        <input value={updateName} onChange={e => setUpdateName(e.target.value)} className="form-control" placeholder="New List Name" required="required" ></input>
                    </form>
                    <button className="delete_list_button" onClick={handleDeleteListModal}>Delete List</button>
                </div>
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
                <ListItems />
            </div>
        );
    } else {
        return (
            <div className="list_content">
                <h1>Welcome, {user.first_name}! You have no list selected. </h1>
            </div>
        );
    }
}