import React from 'react';
import cl from "../../../styles/LeftSide.module.css"
import AuctionGallery from "../../AuctionGallery"
import InfoIcon from "../../../assets/info.svg"

const LeftSide = ({auction}) => {
    return (
        <div className={cl.left}>
            <div className={cl.container}>
                <p className={cl.auction__title}>{auction.lotName}</p>

                <div className={cl.auction__info__container}>
                    <div className={cl.gallery__container}>
                        <AuctionGallery imgNames={auction.imgNames}/>
                    </div>

                    <div className={cl.auction__content__container}>
                        <div className={cl.desc__container}>
                            <p className={cl.desc__label}>Описание</p>
                            <div className={cl.desc__block__container}>
                                <div className={cl.desc__view}>
                                    <img className={cl.info__icon} src={InfoIcon} alt=""/>
                                </div>
                                <div className={cl.desc__area}>{auction.lotDesc}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
};

export default LeftSide;