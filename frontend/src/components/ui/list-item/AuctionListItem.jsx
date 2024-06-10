import React from 'react';
import cl from './AuctionListItem.module.css'
import {Link} from "react-router-dom";
import moment from "moment";
import {DATE_TIME_PATTERN} from "../../../util/constants";

const AuctionListItem = ({auction, imageUrl}) => {
    return (
        <div className={cl.container}>
            <div className={cl.img__container}>
                <img src={imageUrl} alt="lot"/>
            </div>
            <div className={cl.content__container}>
                <div className={cl.content__name__price}>
                    <Link
                        className={cl.content__link}
                        to={"/auctions/" + auction.id}
                    >
                        {auction.lotName}
                    </Link>
                    <div className={cl.content__price}>{auction.currentPrice} $</div>
                </div>
                <div className={cl.content__date}>
                    <p>{moment(auction.endDate).format(DATE_TIME_PATTERN)}</p>
                </div>
            </div>
        </div>
    );
};

export default AuctionListItem;
