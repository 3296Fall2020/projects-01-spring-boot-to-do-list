import React, { useContext, useState } from 'react';
import { Context } from '../context';
import './listContent.css'

export default function AddListUser({ show, close }) {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);
    const [email, setEmail] = useState("");

    const getUser = evt => {
        evt.preventDefault();
        console.log(email);
        fetch('http://localhost:8080/user/getUserEmail?email=' + email)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                addUserToList(data.id);
                setEmail("")
            }).catch((exception) => {
                console.log(exception);
            });
    };

    const addUserToList = (id) => {
        console.log("list id: " + list.id);
        console.log("user id: " + id);
        let url = 'http://localhost:8080/list/addUserToList?user_id=' + id + '&list_id=' + list.id;
        fetch(url, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            console.log(response);
            fetchListUsers(list);
            close(false);
        })
            .catch((exception) => {
                console.log(exception);
            });
    }


    return (
        <div className="add_user_list_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={getUser}>
                <h3>Add email of new member</h3>
                <button id="close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} className="form-control" placeholder="Email" required="required" />
                </div>
                <button type="submit" >Add User</button>
            </form>
        </div>
    )
}
