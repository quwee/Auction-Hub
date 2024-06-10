import React from 'react';
import AuctionListItem from "../list-item/AuctionListItem";
import cl from './AuctionList.module.css'

const AuctionList = ({auctions, imageUrl}) => {
    return (
        <div className={cl.container}>
            {auctions.map(auction =>
                <AuctionListItem auction={auction} imageUrl={imageUrl} key={auction.id}/>
            )}
        </div>
    );
};

export default AuctionList;