import React, { useContext } from 'react';
import { Context } from '../context';
import './listContent.css'

export default function DeleteList({ show, close }) {
    const {list, setList, fetchLists } = useContext(Context);


    const deleteList = evt => {
        evt.preventDefault();
        document.body.style.cursor='wait';
        console.log(list.id);
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/list/deleteList/' + list.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            fetchLists();
            setList({"list_id": list.id, "list_name": null});
            document.body.style.cursor='default';
            close(false);
        })
            .catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("could not delete list, please try again");
                close(false);
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