import React from 'react';
import cl from './Loader.module.css'

const Loader = ({hidden, style}) => {
    return (
        <div hidden={hidden} style={style} className={cl.loader}>

        </div>
    );
};

export default Loader;