import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { NavigationBar, ButtonEvent, ItemTable } from "../../components";
import { Container } from "react-bootstrap";


function Items () {
    const [authenticated, setauthenticated] = useState(null);
    useEffect(() => {
    const loggedInUser = localStorage.getItem("authenticated");
    if (loggedInUser) {
        setauthenticated(loggedInUser);
    }
}, []);

    return (
        <div >
            <NavigationBar />
            <ButtonEvent param={'item'} />
            <ItemTable />
        </div>
    );
};

export { Items }