import React from 'react';
import NavbarWithMenu from "../components/NavbarWithMenu";
import cl from "../styles/NotFound.module.css"

const NotFound = () => {
    return (
        <div>
            <NavbarWithMenu/>
            <h1 className={cl.header}>404 Not Found</h1>
        </div>
    );
};

export default NotFound;