FROM node

ADD /wordservice /wordservice

WORKDIR /wordservice

EXPOSE 5000

CMD ["npm", "install"]

CMD ["npm", "start"]