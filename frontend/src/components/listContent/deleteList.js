import React, { useContext } from 'react';
import { Context } from '../context';
import './listContent.css'

export default function DeleteList({ show, close }) {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);


    const deleteList = evt => {
        evt.preventDefault();
        console.log(list.id);
        close(false);
        let url = 'http://localhost:8080/list/deleteList/' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchLists();
            setList({"list_id": list.id, "list_name": null})
            close(false);
        })
            .catch((exception) => {
                console.log(exception);
            });
    }


    return (
        <div className="delete_list_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={deleteList}>
                <button className="delete_list_close" type="button" onClick={() => { close(false) }}>&times;</button>
                <h3>Are you sure you want to delete list {list.list_name}?</h3>
                <button className="delete_list_submit" type="submit" >Delete</button>
            </form>
        </div>
    )
}