import React, { useContext, useState } from 'react';
import { Context } from '../context';
import './sidePanel.css'

export default function AddList({ show, close }) {
    const [user] = useContext(Context);
    const [listName, setListName] = useState("");


    const addList = evt => {
        evt.preventDefault();
        console.log("adding list: " + listName);
        console.log("adding user: " + user.email + " and list to user_list table");
        setListName("");
        close(false);
    };

    return (
        <div className="add_list_form" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={addList}>
                <button id="close" type="button" onClick={() => { close(false) }}>&times;</button>
                <input type="text" value={listName} onChange={e => setListName(e.target.value)} className="form-control" placeholder="new list" required="required" />
                <button type="submit" >Add</button>
            </form>
        </div>
    );
}