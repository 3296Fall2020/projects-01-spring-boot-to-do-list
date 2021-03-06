import React, { useContext, useState } from 'react';
import { Context } from '../context';
import './sidePanel.css'

export default function AddList({ show, close }) {
    const {user, fetchLists } = useContext(Context);
    const [listName, setListName] = useState("");


    const addList = evt => {
        evt.preventDefault();
        document.body.style.cursor='wait';
        console.log("adding list: " + listName);
        console.log("adding user: " + user.email + " and list to user_list table");
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/list/add?email=' + user.email + '&name=' + listName;
        fetch(url, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchLists();
            setListName("");
            document.body.style.cursor='default';
            close(false);
        }).catch((exception) => {
            document.body.style.cursor='default';
            console.log(exception);
            alert("could not add list, please try again");
            close(false);
        })
    }


return (
    <div className="add_list_popup"  style={{ display: show ? "block" : "none" }}>
        <form onSubmit={addList}>
            <button className="add_list_close" type="button" onClick={() => { close(false) }}>&times;</button>
            <input type="text" value={listName} onChange={e => setListName(e.target.value)} id="addListInput" className="form_control" placeholder=" Add new list here..." required="required" />
            <button className="add_list" type="submit" >Add</button>
        </form>
    </div>
);
}