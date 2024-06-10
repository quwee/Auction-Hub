import React from 'react';
import cl from "./SearchInput.module.css"

const SearchInput = ({query, setQuery, onSearchButtonClick}) => {
    return (
        <div className={cl.search__input__container}>
            <input
                value={query}
                onChange={e => setQuery(e.target.value)}
                className={cl.search__input}
                type="text"
                placeholder="Поиск..."/>
            <button className={cl.search__button} onClick={() => onSearchButtonClick(query)}/>
        </div>
    );
};

export default SearchInput;