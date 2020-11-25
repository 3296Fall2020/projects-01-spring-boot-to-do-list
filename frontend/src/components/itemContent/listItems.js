import React, { useContext, useState, useEffect } from 'react';
import { Context } from '../context'
import CreateItemForm from './createItemForm';
import UpdateItemForm from './updateItemForm'
import Item from './item'
import './listItems.css'


export default function ListItems() {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);
    const [listItems, setListItems] = useState([]);
    const [item, setItem] = useState({});
    const [itemOwner, setItemOwner] = useState(-1);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState(false);

    useEffect(() => {
        setListItems(list.items);
    }, [list, listUsers]);

    const fetchListItems = () => {
        fetch('http://localhost:8080/item/getListItems/' + list.id)
            .then(res => res.json())
            .then(data => {
                setListItems(data);
                console.log(item);
            }).catch((exception) => {
                console.log(exception);
            })
    }

    const handleShowCreateForm = () => {
        setShowCreateForm(!showCreateForm);
    }

    const itemClick = (clickedItem) => {
        setItem(clickedItem);
        getItemOwner(clickedItem);
    }

    const getItemOwner = (clickedItem) => {
        fetch('http://localhost:8080/item/getOwner/' + clickedItem.id)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setItemOwner(data.id);
                handleShowUpdateForm();
            }).catch((exception) => {
                console.log(exception);
            })
    }


    const handleShowUpdateForm = () => {
        setShowUpdateForm(!showUpdateForm);
    }

    if (listItems.length > 0) {
        return (
            <div className="items_list_container">
                <div><button onClick={handleShowCreateForm}>Create</button></div>
                <div className="to_do_container">
                    <h2 className="column_name">TO DO</h2>
                    {listItems.filter(function (item) {
                        return item.completion.id === 1;
                    }).map(function (item) {
                        return (
                            <div key={item.id}> 
                                <Item item={item} click={itemClick} />
                            </div>
                        )
                    })}
                </div>
                <div className="in_progress_container">
                    <h2 className="column_name">IN PROGRESS</h2>
                    {listItems.filter(function (item) {
                        return item.completion.id === 2;
                    }).map(function (item) {
                        return (
                            <div key={item.id}>
                                <Item item={item} click={itemClick} />
                            </div>
                        )
                    })}
                </div>
                <div className="to_do_container">
                    <h2 className="column_name">DONE</h2>
                    {listItems.filter(function (item) {
                        return item.completion.id === 3;
                    }).map(function (item) {
                        return (
                            <div key={item.id}>
                                <Item item={item} click={itemClick} />
                            </div>
                        )
                    })}
                    <UpdateItemForm show={showUpdateForm} close={handleShowUpdateForm} item={item} users={listUsers} setItem={setItem} owner={itemOwner} setOwner={setItemOwner} fetchList={fetchListItems} />
                    <CreateItemForm list={list} show={showCreateForm} close={handleShowCreateForm} users={listUsers} fetchList={fetchListItems}/>
                </div>
            </div>
        )
    } else {
        return (
            <div className="items_list_container">
                <div className="empty_items_list_container">
                    <div><button onClick={handleShowCreateForm}>Create</button></div>
                    <span>¯\_(ツ)_/¯</span>
                    <p>No items could be found</p>
                </div>
                <CreateItemForm list={list} show={showCreateForm} close={handleShowCreateForm} users={listUsers} fetchList={fetchListItems} />
            </div>
        );
    }
}
