FROM golang:1.25.3 AS build
WORKDIR /go/src/golang/lifesteps-api

COPY . .

RUN CGO_ENABLED=0 GOOS=linux go build -o lifesteps-api ./main.go

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=build /go/src/golang/lifesteps-api .
EXPOSE 8080

CMD ["./lifesteps-api"]
