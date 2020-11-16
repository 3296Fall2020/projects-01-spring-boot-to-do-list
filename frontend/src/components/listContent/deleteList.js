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
            close(false);
        })
            .catch((exception) => {
                console.log(exception);
            });
    }


    return (
        <div className="delete_list_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={deleteList}>
                <button id="close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div>Are you sure you want to delete list {list.list_name}?</div>
                <button type="submit" >Delete</button>
            </form>
        </div>
    )
}