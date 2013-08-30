#!/usr/bin/groovy

import java.net.URLDecoder
  
// from http://stackoverflow.com/a/12511583
def visit( node, Closure c ) {
    node.childNodes().each { child ->
    
        if( !child.childNodes() )
            c( child )
            
        visit(child, c)
    }
}

if( args.size() != 1 ) {
    println "Usage: pretty_xml <file.xml>"
    System.exit( 1 )
}

def records = new XmlSlurper().parse( args[0] )

String first = null

visit( records ) { node ->

    String name = String.format( "%20s", node.name )
    String text = URLDecoder.decode( node.text().replace( "\n", " " ).trim().replaceAll("\\s+", " ") )
    
    if( first == name ) println ""
    println "${name} : ${text}"
    
    if( !first ) first = name
}

