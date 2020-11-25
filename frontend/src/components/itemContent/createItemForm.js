import React, { useState, useEffect } from 'react'
import './itemForm.css'


export default function CreateItemForm({ list, show, close, users, fetchList }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [deadline, setDeadline] = useState("");
    const [owner, setOwner] = useState(-1);


    const handleSubmit = (e) => {
        e.preventDefault();
        //MUST FIX DEADLINE
        const data = { task_name: name, description: description, deadline: null };
        let url = 'http://localhost:8080/item/addItem/' + list.id;
        fetch(url, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(data)
        }).then(response => response.json())
            .then(data => {
                console.log(data);
                fetchList();
                close(false);
            }).catch((exception) => {
                console.log(exception);
            });

    }

    return (
        <div className="item_form_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={handleSubmit}>
                <h2 className="form_title">Create New Task</h2>
                <button className="item_form_close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                    <input type="text" value={name} onChange={e => setName(e.target.value)} className="form_name" placeholder="Name" required="required" />
                </div>
                <div className="form-group">
                    <textarea type="text" value={description} onChange={e => setDescription(e.target.value)} rows="15" cols="30" className="form_description" placeholder="Description" />
                </div>
                <div className="form-group">
                    <select type="text" value={owner} className="form_owner" onChange={e => setOwner(e.target.value)}>
                        <option value={-1}>Unassigned </option>
                        {users.map((user) => {
                            return (
                                <option key={user.id} value={user.id}>{user.first_name}</option>
                            )
                        })}
                    </select>
                </div>
                <div className="form-group">
                    <input type="date" value={deadline} onChange={e => setDeadline(e.target.value)} className="form_deadline" placeholder="Date" />
                </div>
                <button className="item_form_create" type="submit" >Create</button>
            </form>
        </div>
    )
}