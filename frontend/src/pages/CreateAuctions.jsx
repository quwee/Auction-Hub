import React, {useState} from 'react';
import ProfileMenu from "../components/ui/menu/ProfileMenu";
import NavbarCreate from "../components/ui/navbar/NavbarCreate";
import CreateAuctionForm from "../components/ui/form/CreateAuctionForm";
import cl from "./CreateAuctions.module.css"

const CreateAuctions = () => {

    const [isMenuOpen, setIsMenuOpen] = useState(false)

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen)
    }

    return (
        <div onClick={() => setIsMenuOpen(false)} className={cl.container} >
            <NavbarCreate
                onBurgerButtonClick={toggleMenu}
                isMenuOpen={isMenuOpen}/>
            <ProfileMenu isOpen={isMenuOpen}/>
            <CreateAuctionForm/>
        </div>
    );
};

export default CreateAuctions;