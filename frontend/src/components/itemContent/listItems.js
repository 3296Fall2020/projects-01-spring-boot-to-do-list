import React, { useContext, useState, useEffect } from 'react';
import { Context } from '../context'
import CreateItemForm from './createItemForm';
import UpdateItemForm from './updateItemForm'
import Item from './item'
import './listItems.css'


export default function ListItems() {
    const {list, listUsers} = useContext(Context);
    const [listItems, setListItems] = useState([]);
    const [item, setItem] = useState({});
    const [itemOwner, setItemOwner] = useState(-1);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState(false);

    useEffect(() => {
        console.log(list.items);
        setListItems(list.items);
        console.log(list.items.length)
        if(list.items.length > 0){
            setItem(list.items[0])
        }else{
            setItem({});
        }
    }, [list]);

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

    const itemClick = (clickedItem) => {
        document.body.style.cursor='wait';
        if (clickedItem.task_name == null) {
            clickedItem.task_name = "";
        }
        if (clickedItem.description == null) {
            clickedItem.description = "";
        }
        if (clickedItem.deadline === null || clickedItem.deadline === "") {
            console.log("no date")
            clickedItem.deadline = "";
        } else {
            formatDate(clickedItem);
        }
        setItem(clickedItem);
        getItemOwner(clickedItem);
    }

    const formatDate = (clickedItem) => {
        let date = clickedItem.deadline;
        let newDate = date.split(" ")[0];
        clickedItem.deadline = newDate;
    }

    const handleShowCreateForm = () => {
        setShowCreateForm(!showCreateForm);
    }

    const getItemOwner = (clickedItem) => {
        fetch('http://localhost:8080/item/getOwner/' + clickedItem.id)
            .then(res => res.json())
            .then(data => {
                setItemOwner(data.id);
                handleShowUpdateForm();
            }).catch((exception) => {
                console.log(exception);
            })
    }


    const handleShowUpdateForm = () => {
        document.body.style.cursor='default';
        setShowUpdateForm(!showUpdateForm);
    }

    if (listItems.length > 0) {
        return (
            <div className="items_list_container">
                <div><button className="create_item_button" onClick={handleShowCreateForm}>Create New Item</button></div>
                <div className="to_do_container">
                    <h2 className="column_name">TO DO</h2>
                    <div className="items">
                        {listItems.filter(function (item) {
                            return item.completion.id === 1;
                        }).map(function (item) {
                            return (
                                <div className="item" key={item.id}>
                                    <Item item={item} click={itemClick} />
                                </div>
                            )
                        })}
                    </div>
                </div>
                <div className="in_progress_container">
                    <h2 className="column_name">IN PROGRESS</h2>
                    <div className="items">
                        {listItems.filter(function (item) {
                            return item.completion.id === 2;
                        }).map(function (item) {
                            return (
                                <div className="item" key={item.id}>
                                    <Item item={item} click={itemClick} />
                                </div>
                            )
                        })}
                    </div>
                </div>
                <div className="done_container">
                    <h2 className="column_name">DONE</h2>
                    <div className="items">
                        {listItems.filter(function (item) {
                            return item.completion.id === 3;
                        }).map(function (item) {
                            return (
                                <div className="item"  key={item.id}>
                                    <Item item={item} click={itemClick} />
                                </div>
                            )
                        })}
                    </div>
                </div>
                <UpdateItemForm show={showUpdateForm} close={handleShowUpdateForm} item={item} users={listUsers} setItem={setItem} owner={itemOwner} setOwner={setItemOwner} fetchList={fetchListItems} />
                <CreateItemForm list={list} show={showCreateForm} close={handleShowCreateForm} users={listUsers} fetchList={fetchListItems} />
            </div>
        )
    } else {
        return (
            <div className="items_list_container">
                <div className="empty_items_list_container">
                    <div><button className="create_item_button" onClick={handleShowCreateForm}>Create New Item</button></div>
                    <span>¯\_(ツ)_/¯</span>
                    <p>No items could be found</p>
                </div>
                <CreateItemForm list={list} show={showCreateForm} close={handleShowCreateForm} users={listUsers} fetchList={fetchListItems} />
            </div>
        );
    }
}
