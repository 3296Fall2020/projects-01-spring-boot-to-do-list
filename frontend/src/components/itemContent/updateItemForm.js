import React, { useEffect, useState } from 'react'
import './itemForm.css'

export default function UpdateItemForm({ show, close, item, setItem, users, owner, setOwner, fetchList }) {
    const [status, setStatus] = useState(1);

    useEffect(() => {
        getStatus();
    }, [item]);

    const getStatus = () => {
        if (item.completion !== undefined) {
            setStatus(item.completion.id);
        } else {
            setStatus(1);
        }
    }

    const handleDelete = (e) => {
        e.preventDefault();
        document.body.style.cursor='wait';
        console.log(item.id);
        let url = 'http://localhost:8080/item/removeItem/' + item.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json',
            })
        }).then((response) => {
            console.log(response);
            fetchList();
            document.body.style.cursor='default';
            close(false);
        })
            .catch((exception) => {
                console.log(exception);
            });
    }

    const handleUpdate = (e) => {
        e.preventDefault();
        document.body.style.cursor='wait';
        const data = { task_name: item.task_name, description: item.description, deadline: item.deadline };
        console.log(data);
        let url = 'http://localhost:8080/item/update/' + item.id;
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(data)
        }).then(response => response.json())
            .then(data => {
                console.log(data);
                updateOwner(); 
            }).catch((exception) => {
                console.log(exception);
            });
    }

    const updateOwner = () => {
        let url = "";
        if (owner > -1) {
            url = 'http://localhost:8080/item/assign?item_id=' + item.id + '&owner_id=' + owner;
        } else {
            url = 'http://localhost:8080/item/retract/' + item.id;
        }
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then(res => res.json())
            .then(data => {
                console.log(data);
                updateStatus();
            }).catch((exception) => {
                console.log(exception);
            });
    }

    const updateStatus = () => {
        let url = 'http://localhost:8080/item/changeStatus?item_id=' + item.id + '&status=' + status;
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        })
            .then(res => res.json())
            .then(data => {
                fetchList();
                document.body.style.cursor='default';
                close(false);
                console.log(data);
            }).catch((exception) => {
                console.log(exception);
            });

    }

    return (
        <div className="item_form_popup" style={{ display: show ? "block" : "none" }}>
            <form>
                <h2 className="form_title">Task</h2>
                <button className="item_form_close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                    <input type="text" value={item.task_name || ""} onChange={e => setItem({ ...item, task_name: e.target.value })} className="form_name" placeholder="Name" required="required" />
                </div>
                <div className="form-group">
                    <textarea type="text" value={item.description || ""} onChange={e => setItem({ ...item, description: e.target.value })} rows="15" cols="30" className="form_description" placeholder="Description" />
                </div>
                <div className="form-group">
                    <input type="datetime-local" value={item.deadline || new Date()} onChange={e => setItem({ ...item, deadline: e.target.value })} className="form_deadline" placeholder="Date" />
                </div>
                <span>Note that selecting a date in the past would set the deadline to 24 hours later from when the request was made.</span>
                <div className="form-group">
                    <select type="text" value={owner} className="form_owner" onChange={e => setOwner(e.target.value)}>
                        <option value={-1}>Unassigned</option>
                        {users.map((user) => {
                            return (
                                <option key={user.id} value={user.id}>{user.first_name}</option>
                            )
                        })}
                    </select>
                </div>
                <div className="form-group">
                    <select type="text" value={status} className="form_completion_status" onChange={e => setStatus(e.target.value)}>
                        <option value={1}>To Do</option>
                        <option value={2}>In Progress</option>
                        <option value={3}>Done</option>
                    </select>
                </div>
                <button className="item_form_update" onClick={handleUpdate} >Update</button>
                <button className="item_form_delete" onClick={handleDelete}>Delete</button>
            </form>
        </div>
    )
}