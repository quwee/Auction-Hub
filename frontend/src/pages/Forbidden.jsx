import React from 'react';
import NavbarWithMenu from "../components/NavbarWithMenu";
import cl from "../styles/NotFound.module.css";

const Forbidden = () => {
    return (
        <div>
            <NavbarWithMenu/>
            <h1 className={cl.header}>403 Forbidden</h1>
        </div>
    );
};

export default Forbidden;