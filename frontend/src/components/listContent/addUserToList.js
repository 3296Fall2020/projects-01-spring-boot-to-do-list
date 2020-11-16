import React, { useContext, useState } from 'react';
import { Context } from '../context';
import './listContent.css'

export default function AddListUser({ show, close }) {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);
    const [email, setEmail] = useState("");

    const addUserToList = evt => {
        evt.preventDefault();
        console.log(list.id);
        console.log(email);
        close(false);
        //get user by email
        //use id to send to add user to list function

        /*
        let url = 'http://localhost:8080/list/deleteList/' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchLists();
            close(false);
        })
            .catch((exception) => {
                console.log(exception);
            });
            */
    }


    return (
        <div className="add_user_list_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={addUserToList}>
                <h3>Add email of new member</h3>
                <button id="close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                        <input type="email" value={email} onChange={e => setEmail(e.target.value)}  className="form-control" placeholder="Email" required="required" />
                </div>
                <button type="submit" >Add User</button>
            </form>
        </div>
    )
}