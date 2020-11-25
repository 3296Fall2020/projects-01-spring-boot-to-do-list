import React, { useEffect, useState } from 'react'
import './itemForm.css'
import { Context } from '../context'

export default function UpdateItemForm({ show, close, item, setItem, users, fetch }) {
    const [status, setStatus] = useState(1);

    useEffect(() => {
        if (item.task_name == null) {
            setItem({ ...item, task_name: "" })
        }
        if (item.description == null) {
            setItem({ ...item, description: "" })
        }
        if (item.deadline == null) {
            setItem({ ...item, deadline: "" })
        }
        getStatus();
    }, [item]);

    const getStatus = () => {
        if(item.completion == undefined){
            setStatus(1);
        }else{
            setStatus(item.completion.id);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(item);
        close(false);
    }

    return (
        <div className="item_form_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={handleSubmit}>
                <button className="item_form_close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                    <input type="text" value={item.task_name} onChange={e => setItem({ ...item, task_name: e.target.value })} className="form_name" placeholder="Name" required="required" />
                </div>
                <div className="form-group">
                    <textarea type="text" value={item.description} onChange={e => setItem({ ...item, description: e.target.value })} rows="15" cols="30" className="form_description" placeholder="Description" />
                </div>
                <div className="form-group">
                    <input type="date" value={item.deadline} onChange={e => setItem({ ...item, deadline: e.target.value })} className="form_deadline" placeholder="Date" />
                </div>
                <div className="form-group">
                    <select type="text" value={status} className="form_completion_status" onChange={e => setStatus(e.target.value)}>
                        <option value={1}>To Do</option>
                        <option value={2}>In Progress</option>
                        <option value={3}>Done</option>
                    </select>
                </div>
                <button className="item_form_update" type="submit" >Update</button>
                <button className="item_form_delete">Delete</button>
            </form>
        </div>
    )
}