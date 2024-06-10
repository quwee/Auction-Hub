import React, {useState} from 'react';
import cl from "./BurgerButton.module.css"

const BurgerButton = ({onClick, isMenuOpen}) => {

    const [isActive, setIsActive] = useState(false)

    const resetIsActive = (e) => {
        e.stopPropagation()
        if(isMenuOpen) {
            setIsActive(false)
        }
        else {
            setIsActive(true)
        }
        onClick()
    }

    return (
        <div onClick={resetIsActive} className={cl.burger__btn}>
            <span className={`${cl.top__line} ${isActive && isMenuOpen ? cl.top__line__active : ''}`}/>
            <span className={`${cl.center__line} ${isActive && isMenuOpen ? cl.center__line__active : ''}`}/>
            <span className={cl.bottom__line}/>
        </div>
    );
};

export default BurgerButton;