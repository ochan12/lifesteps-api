# Build stage
FROM --platform=$BUILDPLATFORM golang:1.25.5 AS build
WORKDIR /src

COPY . .

# Build for the correct target
ARG TARGETOS
ARG TARGETARCH

RUN CGO_ENABLED=0 GOOS=$TARGETOS GOARCH=$TARGETARCH go build -o lifesteps-api ./main.go

# Runtime stage
FROM alpine:latest
RUN apk --no-cache add ca-certificates

WORKDIR /root/
COPY --from=build /src/lifesteps-api .

EXPOSE 8080
CMD ["./lifesteps-api"]
