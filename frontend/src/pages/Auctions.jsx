import React, {useEffect, useState} from 'react';
import cl from "../styles/Auctions.module.css"
import ProfileMenu from "../components/ui/menu/ProfileMenu";
import AuctionList from "../components/ui/list/AuctionList";
import NavbarSearch from "../components/ui/navbar/NavbarSearch";
import axios from "axios";
import AuctionService from "../API/service/AuctionService";
import api from "../API/api";
import Loader from "../components/ui/loader/Loader";

const Auctions = () => {

    const [isMenuOpen, setIsMenuOpen] = useState(false)
    const [auctions, setAuctions] = useState([])
    const [searchedAuctions, setSearchedAuctions] = useState([])
    const [imageUrl, setImageUrl] = useState()
    const [isLoading, setIsLoading] = useState(false)

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen)
    }

    const fetchAuctions = async () => {
        setIsLoading(true)
        let response = await AuctionService.getAllActive()

        if (!response) {
            console.log('error', response)
            alert('Сервер не отвечает')
            setIsLoading(false)
            return
        }

        if (response.status === 200) {
            setAuctions(response.data)
            setSearchedAuctions(response.data)

            if (response.data[0]) {
                try {
                    const resp = await api.get(`auction/load-image/${response.data[0].imagePath}`, {
                        responseType: 'arraybuffer',
                    });

                    const arrayBuffer = resp.data;
                    const byteArray = new Uint8Array(arrayBuffer);
                    const base64Image = btoa(String.fromCharCode.apply(null, byteArray));
                    const imageDataUrl = `data:image/jpeg;base64,${base64Image}`;

                    setImageUrl(imageDataUrl);
                } catch (error) {
                    console.error('Error fetching image:', error);
                }
            }
        } else {
            console.log('error', response)
            alert('Ошибка сервера')
        }
        setIsLoading(false)
    }

    const searchAuctions = (query) => {
        let filteredAuctions = auctions.filter(auction => {
            return auction.title.toLowerCase().includes(query.toLowerCase())
        })
        setSearchedAuctions(filteredAuctions)
    }

    useEffect(() => {
        const fetch = async () => {
            await fetchAuctions()
        }
        fetch()
    }, [])

    useEffect(() => {
        console.log(auctions)
    }, [auctions])

    return (
        <div onClick={() => setIsMenuOpen(false)} style={{height: "100vh"}}>
            <NavbarSearch
                onLogoClick={fetchAuctions}
                onSearchButtonClick={searchAuctions}
                onBurgerButtonClick={toggleMenu}
                isMenuOpen={isMenuOpen}/>
            <ProfileMenu isOpen={isMenuOpen}/>

            {isLoading
                ?
                <div className={cl.loader__container}>
                    <Loader/>
                </div>
                :
                searchedAuctions.length !== 0
                    ?
                    <AuctionList auctions={searchedAuctions} imageUrl={imageUrl}/>
                    :
                    <p className={cl.no__auctions__header}>
                        Пока нет активных аукционов
                    </p>
            }
        </div>
    );
};

export default Auctions;