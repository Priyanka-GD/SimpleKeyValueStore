# Third Phase Commit

- Implement DataStoreDAO as interface
- InMemoryDAO implements DataStoreDAO
- InMemoryDAO will have a composition relationship with NodeResolver
- InMemoryDAO should handle the responsibility of abstracting implementation details
- NodeResolver should support getNode() for an user
- NodeResolver is an interface
- PO - requirement implement NodeResolver as a Modulo function - class
- https://www.baeldung.com/dagger-2